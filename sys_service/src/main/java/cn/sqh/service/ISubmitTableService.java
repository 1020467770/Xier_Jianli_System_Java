package cn.sqh.service;

public interface ISubmitTableService {

    void checkSubmitTable(Integer submitTableId, Integer checkerId, Integer checkResultStatus) throws Exception;

    void startCheckSubmitTable(Integer submitTableId)throws Exception;
}
