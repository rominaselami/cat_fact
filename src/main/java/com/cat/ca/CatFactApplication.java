package com.cat.ca;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CatFactApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatFactApplication.class, args);
    }
    /** this class is to handle HTTP requests, manage the list of cat facts,
     *  and coordinate the flow of data between the client,
     *  server, and the underlying business logic**/
    @Controller
    public static class CatFactController {
        private List<CatFact> catFacts = new ArrayList<>();
     /**this method is to handle the GET request to the root ("/") endpoint,
      * add the catFacts
      * list as an attribute to the model, and render the index **/
        @GetMapping("/")
        public String index(Model model) {
            model.addAttribute("catFacts", catFacts);
            return "index";
        }

        /**  this method is to handle the removal of a cat fact from the catFacts
        list based on the provided index and redirect the user back to
        the index page to reflect the updated list of cat facts **/
        @GetMapping("/redirect/{index}")
        public String redirect(@PathVariable int index) {
            if (index >= 0 && index < catFacts.size()) {
                catFacts.remove(index);
            }
            return "redirect:/";
        }
    /**this method is to fetch a random cat fact from an external API,
     store it in the catFacts list, and redirect the user
     back to the index page to display the updated list of cat facts **/
        @PostMapping("/addFact")
        public String addFact() {
            String url = "https://catfact.ninja/fact?max_length=114";
            WebClient.Builder builder = WebClient.builder();
            WebClient.ResponseSpec response = builder.build()
                    .get()
                    .uri(url)
                    .retrieve();
            CatFact catFact = response.bodyToMono(CatFact.class).block();
            if (catFact != null) {
                catFacts.add(catFact);
            }
            return "redirect:/";
        }
        /** method  class to handle the search functionality **/
        @GetMapping("/search")
        public String search(@RequestParam(required = false) String searchTerm, Model model) {
            List<CatFact> searchResults = new ArrayList<>();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                for (CatFact catFact : catFacts) {
                    if (catFact.getFact().contains(searchTerm)) {
                        searchResults.add(catFact);
                    }
                }
            }
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("searchResults", searchResults);
            return "search-results";
        }

    }
}
