package com.transfar.smarttda.warehouse.idata;

import com.transfar.smarttda.bean.ExceptionBean;

import java.util.List;

/**
 * Created by stayli on 2017/3/30.
 */

public interface IAppError {
    List<ExceptionBean> getExceptionLists();
    void delAllException();
}
