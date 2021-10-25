/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.response;

/**
 *
 * @author daniel
 */
public class Response {
    
    private final int type;
    private final Object data;
    private final String status;
    
    public static final String OK = "OK";
    public static final String FAIL = "FAIL";
    public static final String NORMAL = "NORMAL";

    public Response(int type, Object data, String status) {
        this.type = type;
        this.data = data;
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
    
}
