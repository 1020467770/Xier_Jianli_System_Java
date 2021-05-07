package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.service.ISubmitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RequestMapping("/submitTable")
@RestController
public class SubmitTableController {

    @Autowired
    private ISubmitTableService submitTableService;

    @RequestMapping(value = "/startCheckSubmitTable.do", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @RolesAllowed({"GADMIN"})
    @ResponseBody
    public Result startCheckSubmitTable(@RequestParam("submitTableId") Integer submitTableId) throws Exception {
        submitTableService.startCheckSubmitTable(submitTableId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/checkSubmitTable.do", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @RolesAllowed({"GADMIN"})
    @ResponseBody
    public Result checkSubmitTable(@RequestParam("submitTableId") Integer submitTableId,
                                   @RequestParam(value = "checkerId") Integer checkerId,
                                   @RequestParam("checkResultStatus") Integer checkResultStatus) throws Exception {
        submitTableService.checkSubmitTable(submitTableId, checkerId, checkResultStatus);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }
}
