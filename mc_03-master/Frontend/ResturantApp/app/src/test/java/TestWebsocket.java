import android.content.Context;
import android.os.Build;
import android.widget.EditText;

import com.example.resturantapplication.BufferPage;
import com.example.resturantapplication.WebsocketActivity;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.nio.Buffer;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class TestWebsocket {

    @Before
    public void setUpMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {

        WebsocketActivity ChatTest = new WebsocketActivity();
        BufferPage bp = new BufferPage();
        assertEquals("50014", ChatTest.getZip());
    }

}
