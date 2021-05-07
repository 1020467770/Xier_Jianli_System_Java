package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.service.ISubmitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/submitTable")
@ResponseBody
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
