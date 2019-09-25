package stefanowicz.kacper.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stefanowicz.kacper.exception.AppException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {

    private final String jsonFilename;
    private final Gson gson =  new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename){ this.jsonFilename = jsonFilename; }


    public void toJson(final T element){

        if( element == null ) {
            throw new AppException("json converter - to json method - element is null");
        }

        try( FileWriter writer = new FileWriter(jsonFilename) ) {
            gson.toJson(element, writer);
        }
        catch( Exception e ) {
            throw new AppException("json converter - to json method - " + e.getMessage());
        }
    }

    public Optional<T> fromJson() {
            try(FileReader reader = new FileReader(jsonFilename) ) {
                return Optional.of(gson.fromJson(reader, type));
            }
            catch (Exception e ) {
                throw new AppException("json converter - from json method - " + e.getMessage());
            }
    }
}
