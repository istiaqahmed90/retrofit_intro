package com.journaldev.retrofitintro.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RequiredVarification {

    @SerializedName("is_backoffice_varification_required")
    public Integer is_backoffice_varification_required;
    @SerializedName("is_video_varification_required")
    public Integer is_video_varification_required;

}
