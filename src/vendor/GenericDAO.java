package vendor;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {

    final Class<T> clase;

    protected File archivo;

    public GenericDAO(Class<T> clase, String file) throws Exception {
        this.clase = clase;

        this.archivo = new File(System.getProperty("user.dir") + "\\Archivos\\" + file);

        this.archivo.createNewFile();
    }

    public List<T> getAll() throws Exception {

        List<T> list = new ArrayList<T>();

        FileReader f = new FileReader(this.archivo);

        BufferedReader buffer = new BufferedReader(f);

        try {

            String cadena = buffer.readLine();

            if(cadena == null){
                this.saveAll(list);
                cadena = "[]";
            }

            JsonParser parser = new JsonParser();
            JsonArray gsonArr = parser.parse(cadena).getAsJsonArray();
            Gson g = new Gson();

            for (JsonElement js: gsonArr) {
                list.add(g.fromJson(js, clase));
            }

            buffer.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return list;

    }

    public void saveAll(List<T> list) throws Exception {
        Gson g = new Gson();
        String texto = g.toJson(list);
        FileWriter fileWriter = new FileWriter(this.archivo);
        fileWriter.write(texto);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.close();
    }

}
