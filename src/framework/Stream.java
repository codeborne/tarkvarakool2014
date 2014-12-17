package framework;

import com.google.common.io.ByteStreams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class Stream extends Result {

  private InputStream stream;
  private String contentType;
  private boolean inline;
  private String filename;

  public Stream(InputStream stream, String contentType) {
    this.stream = stream;
    this.contentType = contentType;
    this.inline = true;
  }

  public Stream(InputStream stream, String contentType, String filename) {
    this.stream = stream;
    this.contentType = contentType;
    this.filename = filename;
  }

  @Override
  void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType(contentType);
    response.addHeader("Content-disposition", inline ? "inline" : "attachment;filename=\"" + filename + "\"");
    ByteStreams.copy(stream, response.getOutputStream());
  }
}
