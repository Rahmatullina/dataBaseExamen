package com.DataBaseTask.DataBaseTask.Controllers;

import com.DataBaseTask.DataBaseTask.POJOClasses.DataBaseStructureClass;
import org.springframework.transaction.annotation.Transactional;

public interface DataBaseController {
    @Transactional
    DataBaseStructureClass getAllDataInJSON();
    @Transactional
    void fillDataBasInJSON(DataBaseStructureClass dataBaseStructureClass);
}
