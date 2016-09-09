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

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import me.geenee.Bootstrap;
import me.geenee.dao.ImageDao;
import me.geenee.dao.TagDao;
import me.geenee.entities.Image;
import me.geenee.entities.Tag;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Tests for REST API of {@link UserController}.
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@SuppressWarnings({ "PMD.TestClassWithoutTestCases", "PMD.TooManyMethods" })
public class ImageControllerTest extends AbstractRestTest {

    /**
     * Image dao.
     */
    @Autowired
    private transient ImageDao images;

    /**
     * Tag dao.
     */
    @Autowired
    private transient TagDao tags;

    /**
     * Can read the list of images.
     * @throws Exception If something goes wrong.
     */
    @Test
    public final void readsList() throws Exception {
        this.getMvc()
            .perform(
                MockMvcRequestBuilders
                    .get(ImageController.PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(this.listHasSize(Matchers.not(0)));
    }

    /**
     * Can find images by tags.
     * @throws Exception If something goes wrong.
     */
    @Test
    public final void findsByTags() throws Exception {
        final Tag first = this.tags.saveAndFlush(new Tag(null, "new tag 1"));
        final Tag second = this.tags.saveAndFlush(new Tag(null, "new tag 2"));
        this.images.save(
            Arrays.asList(
                new Image(
                    null,
                    new Date(),
                    "New Title 1",
                    new HashSet<>(
                        Arrays.asList(first)
                    )
                ),
                new Image(
                    null,
                    new Date(),
                    "New Title 2",
                    new HashSet<>(
                        Arrays.asList(second)
                    )
                )
            )
        );
        this.getMvc()
            .perform(
                MockMvcRequestBuilders
                    .get(ImageController.PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("tag", first.getName(), second.getName())
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(this.listHasSize(Matchers.is(2)));
    }

}
