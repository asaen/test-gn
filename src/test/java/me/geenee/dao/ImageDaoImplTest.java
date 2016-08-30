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
package me.geenee.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import me.geenee.Bootstrap;
import me.geenee.entities.Image;
import me.geenee.entities.Tag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for {@link ImageDaoImpl}.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class ImageDaoImplTest {

    /**
     * Image dao.
     */
    @Autowired
    private transient ImageDao dao;

    /**
     * Can find single image.
     */
    @Test
    public final void findsSingleImage() {
        final String tag = "tag-1";
        this.dao.saveAndFlush(
            new Image(
                null,
                new Date(),
                "title",
                new HashSet<>(Arrays.asList(new Tag(null, tag)))
            )
        );
        this.dao.saveAndFlush(
            new Image(
                null,
                new Date(),
                "new title",
                new HashSet<>(Arrays.asList(new Tag(null, "tag--1")))
            )
        );
        Assert.assertEquals(1, this.dao.findByTags(Arrays.asList(tag)).size());
    }

}
