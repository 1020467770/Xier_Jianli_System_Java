package cn.sqh.service.impl;

import cn.sqh.dao.ISubmitTableDao;
import cn.sqh.domain.SubmitTable;
import cn.sqh.service.ISubmitTableService;
import cn.sqh.utils.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Service
@Transactional
public class SubmitTableServiceImpl implements ISubmitTableService {

    @Autowired
    private ISubmitTableDao submitTableDao;

    @Autowired
    @Qualifier("taskExecutor")
    private Executor asyncExecutor;

    @Override
    public void checkSubmitTable(Integer submitTableId, Integer checkerId, Integer checkResultStatus) throws Exception {
        if (checkResultStatus != SubmitTable.CHECKEDTYPE_CHECKED_SUCCESS
                && checkResultStatus != SubmitTable.CHECKEDTYPE_CHECKED_FAILED) {
            throw new Exception();
        }
        SubmitTable table = submitTableDao.findSubmitTableBySubmitTableId(submitTableId);
        String content = table.getContent();
        ObjectMapper mapper = new ObjectMapper();
        Map contentMap = mapper.readValue(content, Map.class);
        List<String> o = (List<String>) contentMap.get("邮箱");
        if (o != null) {
            String email = o.get(0);
            if (email != null) {
                asyncExecutor.execute(() -> {
                    if (checkResultStatus == SubmitTable.CHECKEDTYPE_CHECKED_SUCCESS) {
                        MailUtils.sendMail(email, "恭喜您的简历通过审核", "关于您投递的简历的回复");
                    } else if (checkResultStatus == SubmitTable.CHECKEDTYPE_CHECKED_FAILED) {
                        MailUtils.sendMail(email, "很可惜，您的简历未通过审核", "关于您投递的简历的回复");
                    }
                });

            }
        }
        submitTableDao.checkSubmitTable(submitTableId, checkerId, checkResultStatus);
    }

    @Override
    public void startCheckSubmitTable(Integer submitTableId) throws Exception {
        submitTableDao.startCheckSubmitTable(submitTableId);
    }
}
