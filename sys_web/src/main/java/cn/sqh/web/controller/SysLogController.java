package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.domain.SysLog;
import cn.sqh.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/sysLog")
@RestController
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/findAll.do")
    @RolesAllowed("SADMIN")
    public Result findAll() throws Exception {
        List<SysLog> sysLogList = sysLogService.findAll();
        return Result.build(Result.RESULTTYPE_SUCCESS, sysLogList);
    }

    @GetMapping("/findByPageNum.do")
    @RolesAllowed("SADMIN")
    public Result findByPageNum(@RequestParam("pageNum")Integer pageNum) throws Exception {
        List<SysLog> sysLogList = sysLogService.findByPage(pageNum);
        return Result.build(Result.RESULTTYPE_SUCCESS, sysLogList);
    }
}
