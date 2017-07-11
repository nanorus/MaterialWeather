
package com.example.nanorus.materialweather.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsPojo {

    @SerializedName("channel")
    @Expose
    private ChannelPojo channel;

    public ChannelPojo getChannel() {
        return channel;
    }

    public void setChannel(ChannelPojo channel) {
        this.channel = channel;
    }

}
