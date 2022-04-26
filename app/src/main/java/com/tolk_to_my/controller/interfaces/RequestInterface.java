package com.tolk_to_my.controller.interfaces;

import com.tolk_to_my.model.Request;

public interface RequestInterface {

    void accept(Request model, int position);

    void delete(Request model);
}
