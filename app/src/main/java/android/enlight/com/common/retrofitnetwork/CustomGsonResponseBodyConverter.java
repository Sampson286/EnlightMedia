package android.enlight.com.common.retrofitnetwork;

import android.enlight.com.common.utils.MD5Utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;


/**
 * Created by zyc on 2017/7/19.
 * 处理API返回数据的前缀
 */

public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            String result = response.trim();
            boolean hasBody = result.length() >= 32;
            if (hasBody) {
                String md5 = result.substring(0, 32);
                String payload = result.substring(32);
                if (md5.equals(MD5Utils.encode(payload))) {
                    MediaType contentType = value.contentType();
                    Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
                    InputStream inputStream = new ByteArrayInputStream(payload.getBytes());
                    Reader reader = new InputStreamReader(inputStream, charset);
                    JsonReader jsonReader = gson.newJsonReader(reader);
                    return adapter.read(jsonReader);
                }
            }else{
                JsonReader jsonReader = gson.newJsonReader(value.charStream());
                return adapter.read(jsonReader);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        } finally {
            value.close();
        }
        return null;
    }

}
