package com.prajwal.manytomany.Model;

import java.util.List;

/*
{
"cIds":[1,2,3,4,5],
"adIds":[6]
}
 */
public class CusAdd {
    private List<Integer> cIds;
    private List<Integer> adIds;


    public List<Integer> getcIds() {
        return cIds;
    }

    public void setcIds(List<Integer> cIds) {
        this.cIds = cIds;
    }

    public List<Integer> getAdIds() {
        return adIds;
    }

    public void setAdIds(List<Integer> adIds) {
        this.adIds = adIds;
    }
}
