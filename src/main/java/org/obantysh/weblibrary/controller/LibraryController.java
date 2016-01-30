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

@Controller
public class LibraryController {
    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/changeBook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Catalog changeBook(@RequestBody Catalog catalog) {
        return catalogService.handleCatalog(catalog);
    }
}