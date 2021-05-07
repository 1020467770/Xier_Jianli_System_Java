package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.domain.SubmitTable;
import cn.sqh.domain.Table;
import cn.sqh.domain.TableField;
import cn.sqh.domain.result.UploadFileResult;
import cn.sqh.service.IMessageService;
import cn.sqh.service.IStorageService;
import cn.sqh.service.ITableService;
import cn.sqh.service.ITablesFieldService;
import cn.sqh.utils.MD5;
import cn.sqh.web.interfaces.LimitRequest;
import cn.sqh.web.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/table")
@RestController
@Slf4j
public class TableController {

    @Autowired
    private ITableService tableService;

    @Autowired
    private ITablesFieldService fieldService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private IMessageService messageService;

    @RequestMapping(value = "/addTableFieldsToTable.do", method = RequestMethod.POST)
    @RolesAllowed("GADMIN")
    public Result addTableFieldsToTable(@RequestParam(name = "tableId", required = true) Integer tableId,
                                        @RequestParam(name = "fieldIds", required = true) Integer[] fieldIds) throws Exception {
        tableService.addTableFieldToTable(tableId, fieldIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/addOneTableFieldToTable.do", method = RequestMethod.POST)
    @RolesAllowed("GADMIN")
    public Result addOneTableFieldToTable(@RequestParam(name = "tableId", required = true) Integer tableId,
                                          @RequestParam(name = "fieldId", required = true) Integer fieldId) throws Exception {
        tableService.addTableFieldToTable(tableId, fieldId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removeTableFieldsToTable.do", method = RequestMethod.POST)
    @RolesAllowed("GADMIN")
    public Result removeTableFieldsToTable(@RequestParam(name = "tableId", required = true) Integer tableId,
                                           @RequestParam(name = "tableId", required = true) Integer[] fieldIds) throws Exception {
        tableService.removeTableFieldFromTable(tableId, fieldIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removeOneTableFieldToTable.do", method = RequestMethod.POST)
    @RolesAllowed("GADMIN")
    public Result removeOneTableFieldToTable(@RequestParam(name = "tableId", required = true) Integer tableId,
                                             @RequestParam(name = "fieldId", required = true) Integer fieldId) throws Exception {
        tableService.removeTableFieldFromTable(tableId, fieldId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @LimitRequest(count = 5)//同一个ip5分钟内最多提交5次
    @RequestMapping(value = "/submitTable.do", method = RequestMethod.POST)
    @PermitAll
    public Result submitTable(@RequestParam("tableId") Integer tableId,
                              @RequestParam(value = "uploadFile") MultipartFile uploadFile,
                              HttpServletRequest request) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.remove("tableId");
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFile.getOriginalFilename()));
        if (!StringUtil.isFileTypeCorrect(fileName, "txt","doc","docx")) {
            return Result.build(Result.RESULTTYPE_DINIED, "文件类型有误");
        }
        String fileFormatName = MD5.MD5Encode(UUID.randomUUID().toString(), "utf-8") + "@" + fileName;
        storageService.storeFile(uploadFile, fileName, fileFormatName);
        SubmitTable submitTable = new SubmitTable();
        ObjectMapper mapper = new ObjectMapper();
        submitTable.setContent(mapper.writeValueAsString(parameterMap));
        submitTable.setFileName(fileFormatName);
        submitTable.setCheckedStatus(SubmitTable.CHECKEDTYPE_NOTSTART);
        submitTable.setUpdateDate(new Date());
        tableService.saveNewSubmitTable(tableId, submitTable);
        messageService.notifyNewMessageToGroupByTableId("有一个新的简历投递到你的小组了", tableId, true);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/table/downloadFile.do/")
                .path(fileFormatName).toUriString();
        return Result.build(Result.RESULTTYPE_SUCCESS, new UploadFileResult(fileName, fileFormatName, fileDownloadUri, uploadFile.getContentType(), uploadFile.getSize()));
    }

    @RequestMapping(value = "/findAllSubmitsByTableId.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"GADMIN"})
    @ResponseBody
    public Result findAllSubmitsByTableId(@RequestParam("tableId") Integer tableId) throws Exception {
        List<SubmitTable> submitTableList = tableService.findAllSubmitsByGroupId(tableId);
        return Result.build(Result.RESULTTYPE_SUCCESS, submitTableList);
    }

    /**
     * 查询所有可被添加的表格字段
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findAllTableFields.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"GADMIN"})
    @ResponseBody
    public Result findAllTableFields() throws Exception {
        List<TableField> fieldList = fieldService.findAll();
        return Result.build(Result.RESULTTYPE_SUCCESS, fieldList);
    }

    /**
     * 根据tableId获取简历模板
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findFormworkByTableId.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    @ResponseBody
    public Result findFormworkByTableId(@RequestParam("tableId") Integer tableId) throws Exception {
        Table table = tableService.finFormworkByTableId(tableId);
        return Result.build(Result.RESULTTYPE_SUCCESS, table);
    }

    @GetMapping("/downloadFile.do/{fileFormatName:.+}")
    @RolesAllowed({"GADMIN"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileFormatName, HttpServletRequest request) {
        Resource resource = storageService.loadFileAsResource(fileFormatName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.debug("无法确定文件类型");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
