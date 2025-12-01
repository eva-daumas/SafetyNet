    package com.safetynet.alert.repository;

    import com.safetynet.alert.model.Data;
    //import org.apache.tomcat.util.http.fileupload.IOUtils;
    import com.safetynet.alert.model.Person;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.stereotype.Component;

    import java.io.*;
    import java.nio.charset.StandardCharsets;

    import com.jsoniter.JsonIterator;

    import org.apache.commons.io.IOUtils;



    @Component
    public class DataHandler {

        private final Data data;

        public DataHandler() throws IOException {
            String temp = getFromRessource("data.json");
            this.data = JsonIterator.deserialize(temp, Data.class);
        }

        private String getFromRessource(String s) throws IOException {
            InputStream is = new ClassPathResource(s).getInputStream();
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }

        public Data getData() {
            return data;
        }

        // Ajouter une personne
        public void savePerson(Person person) {
            if (person == null) return;
            data.getPersons().add(person);  // utiliser directement data
            save();  // appelle la vraie méthode save()
        }

        // Sauvegarde le JSON dans le fichier si nécessaire
        public void save() {
            // ici tu peux écrire le code pour sauvegarder data dans data.json
            // ex : JsonStream.serializeToFile("data.json", data);
        }
    }

