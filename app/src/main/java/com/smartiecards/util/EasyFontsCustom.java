package com.smartiecards.util;

/*
 * Copyright (C) 2015 EasyFonts
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.graphics.Typeface;

import com.smartiecards.R;
import com.vstechlab.easyfonts.EasyFonts;


public final class EasyFontsCustom{

    private EasyFontsCustom(){}

    /**
     * Roboto-Thin font face
     * @param context context
     * @return Typeface object for Roboto Thin
     */
    public static Typeface screamReal(Context context){
        return FontSourceProcessorCustom.process(R.raw.scream_real, context);
    }

    public static Typeface opensansSemibold(Context context){
        return FontSourceProcessorCustom.process(R.raw.opensans_semibold, context);
    }


    public static Typeface notosanScherokee_Regular(Context context){
        return FontSourceProcessorCustom.process(R.raw.notosanscherokee_regular, context);
    }


    public static Typeface avenirnext_TLPro_Regular(Context context){
        return FontSourceProcessorCustom.process(R.raw.avenirnext_tlpro_regular, context);
    }


    public static Typeface avenirnext_TLPro_Medium(Context context){
        return FontSourceProcessorCustom.process(R.raw.avenirnext_tlpro_medium, context);
    }

    public static Typeface avenirnext_TLPro_Demi(Context context){
        return FontSourceProcessorCustom.process(R.raw.avenirnexttlpro_demi, context);
    }

}
