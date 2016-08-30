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
package me.geenee.conf;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import me.geenee.dao.ImageDao;
import me.geenee.dao.TagDao;
import me.geenee.entities.Image;
import me.geenee.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Fills the database with the sample data.
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Configuration
public class SampleDataConfiguration {

    /**
     * Amount of tags.
     */
    private static final int TAGS_AMOUNT = 1;

    /**
     * Amount of images.
     */
    private static final int IMAGES_AMOUNT = 10;

    /**
     * Image DAO.
     */
    @Autowired
    private transient ImageDao images;

    /**
     * Tag DAO.
     */
    @Autowired
    private transient TagDao tags;

    /**
     * Fills database with the data.
     */
    @PostConstruct
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public final void fill() {
        this.createTags();
        for (int idx = 1; idx <= IMAGES_AMOUNT; ++idx) {
            this.images.saveAndFlush(
                new Image(
                    null,
                    new Date(),
                    String.format("image #%s", idx),
                    this.randomTags()
                )
            );
        }
    }

    /**
     * Creates tags in the DB.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private void createTags() {
        for (int idx = 1; idx <= TAGS_AMOUNT; ++idx) {
            this.tags.saveAndFlush(
                new Tag(null, String.format("tag #%s", idx))
            );
        }
    }

    /**
     * Generates random set of tags.
     * @return Set of tags.
     * @checkstyle ParameterNameCheck (25 lines)
     */
    private Set<Tag> randomTags() {
        final int amount = this.random(1, TAGS_AMOUNT);
        final Set<Tag> result = new TreeSet<>(
            new Comparator<Tag>() {
                @Override
                public int compare(final Tag o1, final Tag o2) {
                    Long first = Long.MIN_VALUE;
                    Long second = Long.MIN_VALUE;
                    if (o1 != null) {
                        first = o1.getId();
                    }
                    if (o2 != null) {
                        second = o2.getId();
                    }
                    return first.compareTo(second);
                }
            }
        );
        for (int idx = 0; idx < amount; ++idx) {
            result.add(this.tags.findOne((long) this.random(1, TAGS_AMOUNT)));
        }
        return result;
    }

    /**
     * Generates a random number in the range [min; max].
     * @param min Min value in the range.
     * @param max Max value in the range.
     * @return Integer.
     */
    private int random(final int min, final int max) {
        return min + (int) (Math.random() * max);
    }

}
