/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alexey Saenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.geenee.controller;

import java.util.List;
import me.geenee.dao.ImageDao;
import me.geenee.entities.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API for managing images.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@RestController
@RequestMapping(
    value = ImageController.PATH,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
public class ImageController {

    /**
     * REST path.
     */
    public static final String PATH = "/rest/image";

    /**
     * Image DAO.
     */
    @Autowired
    private transient ImageDao dao;

    /**
     * Finds images by tags.
     * @param tags Tags.
     * @return Found images, if no tags are given returns all images.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final Iterable<Image> find(
        @RequestParam(name = "tag", required = false) final List<String> tags
    ) {
        List<Image> result;
        if (tags == null || tags.isEmpty()) {
            result = this.dao.findAll();
        } else {
            result = this.dao.findByTags(tags);
        }
        return result;
    }

}
