package org.obantysh.weblibrary.controller;

import org.obantysh.weblibrary.model.Catalog;
import org.obantysh.weblibrary.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class LibraryController {
    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/changeBook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Catalog changeBook(@RequestBody Catalog catalog) {
        return catalogService.handleCatalog(catalog);
    }

    @RequestMapping(value = "/changeBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Catalog changeBook() {
        return catalogService.handleCatalog(new Catalog());
    }
}