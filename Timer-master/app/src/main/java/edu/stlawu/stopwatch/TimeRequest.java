package edu.stlawu.stopwatch;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TimeRequest extends StringRequest {

    final static private String URL = "http://hssoft.kr:9878/UserTimer.php";
    private Map<String, String> parameters;

    public TimeRequest(String userID, String userTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파마미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userTime", userTime);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
