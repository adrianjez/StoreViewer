package com.hqapps.storeviewer.api;

import com.hqapps.storeviewer.data.model.Store;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class StoreResponse {
    @ElementList(name = "store", inline = true, required = false)
    public List<Store> store;
}
