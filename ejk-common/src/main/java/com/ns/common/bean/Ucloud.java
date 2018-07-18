package com.ns.common.bean;

public class Ucloud {
    /**
     * api公钥
     */
    private String publicKey;
    /**
     * api私钥
     */
    private String privateKey;
    /**
     * api请求地址
     */
    private String serverUrl;

    public Ucloud(String publicKey, String privateKey, String serverUrl) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.serverUrl = serverUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

}
