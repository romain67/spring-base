package library.format;

import com.roms.library.format.StringFormat;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by romain on 16/09/2016.
 */
public class StringFormatTest {

    @Test
    public void canonicalize() throws Exception {
        Assert.assertEquals("", StringFormat.canonicalize("(?$+*=/)"));
        Assert.assertEquals("abeeaue-ok30", StringFormat.canonicalize("AbéèàüÉ!_oK30!"));
    }

}