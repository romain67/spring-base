package com.roms.library.format;

import org.junit.Assert;
import org.junit.Test;

public class StringFormatTest {

    @Test
    public void testCanonicalize() throws Exception {
        Assert.assertEquals("", StringFormat.canonicalize("(?$+*=/)"));
        Assert.assertEquals("abeeaue-ok30", StringFormat.canonicalize("AbéèàüÉ!_oK30!"));
    }

}