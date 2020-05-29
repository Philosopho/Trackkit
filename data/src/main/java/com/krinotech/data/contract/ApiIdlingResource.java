package com.krinotech.data.contract;

import androidx.test.espresso.IdlingResource;

public interface ApiIdlingResource extends IdlingResource {

    void setIdleState(boolean isIdle);
}
