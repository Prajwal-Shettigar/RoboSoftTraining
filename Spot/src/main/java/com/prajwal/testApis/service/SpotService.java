package com.prajwal.testApis.service;

import java.io.IOException;

public interface SpotService {

    void callSpot(String keyword) throws IOException, InterruptedException;
}
