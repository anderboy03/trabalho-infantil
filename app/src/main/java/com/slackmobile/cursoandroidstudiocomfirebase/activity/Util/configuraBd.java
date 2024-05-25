package com.slackmobile.cursoandroidstudiocomfirebase.activity.Util;

import com.google.firebase.auth.FirebaseAuth;

public class configuraBd {
    private static FirebaseAuth auth;

    public static FirebaseAuth FirebaseAutenticacao(){
        //faze verificação da autenticacao
        if(auth==null){
            auth=FirebaseAuth.getInstance();
        }
        return auth;

    }



}
