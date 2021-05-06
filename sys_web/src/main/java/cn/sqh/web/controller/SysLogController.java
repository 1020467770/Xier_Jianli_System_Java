package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.domain.SysLog;
import cn.sqh.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/sysLog")
@ResponseBody
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/findAll.do")
    @RolesAllowed("SADMIN")
    public Result findAll() throws Exception {
        List<SysLog> sysLogList = sysLogService.findAll();
        return Result.build(Result.RESULTTYPE_SUCCESS, sysLogList);
    }
}
