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

import com.fasterxml.jackson.databind.MapperFeature;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Class has some abstract stuff for testing REST API.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@WebAppConfiguration
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@Slf4j
public abstract class AbstractRestTest {

    /**
     * Mock MVC.
     */
    private transient MockMvc mvc;

    /**
     * Http Message converter.
     */
    private transient MappingJackson2HttpMessageConverter converter;

    /**
     * Web application context.
     */
    @Autowired
    private transient WebApplicationContext context;

    /**
     * Sets mock mvc.
     * @throws Exception If something goes wrong.
     */
    @Before
    public final void setMockMvc() throws Exception {
        this.mvc =
            MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    /**
     * Sets http message converter.
     */
    @Before
    public final void setConverter() {
        this.converter = new MappingJackson2HttpMessageConverter();
        this.converter.getObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);
    }

    /**
     * Gets mock mvc.
     * @return MVC.
     */
    public final MockMvc getMvc() {
        return this.mvc;
    }

    /**
     * Converts the given object to json string.
     * @param obj Given object.
     * @return Json string.
     * @throws IOException If something goes wrong.
     */
    protected final String json(final Object obj) throws IOException {
        final MockHttpOutputMessage result = new MockHttpOutputMessage();
        this.converter.write(obj, MediaType.APPLICATION_JSON_UTF8, result);
        final String json = result.getBodyAsString();
        log.debug("Json of object: {}", json);
        return json;
    }

    /**
     * Converts iterable to list.
     * @param iterable Iterable.
     * @param <T> Type of items.
     * @return List of items.
     */
    protected final <T> List<T> toList(final Iterable<T> iterable) {
        final List<T> result = new LinkedList<T>();
        for (final T item : iterable) {
            result.add(item);
        }
        return result;
    }

    /**
     * Check against size of elements.
     * @param size Size.
     * @return Result matcher.
     */
    protected final ResultMatcher listHasSize(
        final Matcher<? super java.lang.Integer> size
    ) {
        return MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(size));
    }

}
