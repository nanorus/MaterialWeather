
package com.example.nanorus.materialweather.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonPojo {

    @SerializedName("query")
    @Expose
    private QueryPojo query;

    public QueryPojo getQuery() {
        return query;
    }

    public void setQuery(QueryPojo query) {
        this.query = query;
    }

}
