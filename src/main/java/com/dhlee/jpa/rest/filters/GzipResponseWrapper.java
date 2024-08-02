package com.dhlee.jpa.rest.filters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class GzipResponseWrapper extends HttpServletResponseWrapper {

	private GZIPServletOutputStream gzipOutputStream = null;
	private PrintWriter printWriter = null;

	public GzipResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (printWriter != null) {
			throw new IllegalStateException("PrintWriter obtained already - cannot get OutputStream");
		}
		if (gzipOutputStream == null) {
			gzipOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
		}
		return gzipOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (gzipOutputStream != null) {
			throw new IllegalStateException("OutputStream obtained already - cannot get PrintWriter");
		}
		if (printWriter == null) {
			gzipOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
			printWriter = new PrintWriter(
					new OutputStreamWriter(gzipOutputStream, getResponse().getCharacterEncoding()));
		}
		return printWriter;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		} else if (gzipOutputStream != null) {
			gzipOutputStream.flush();
		}
		super.flushBuffer();
	}

	public void finish() throws IOException {
		if (printWriter != null) {
			printWriter.close();
		} else if (gzipOutputStream != null) {
			gzipOutputStream.close();
		}
	}

	private class GZIPServletOutputStream extends ServletOutputStream {

		private GZIPOutputStream gzipOutputStream;

		public GZIPServletOutputStream(ServletOutputStream output) throws IOException {
			this.gzipOutputStream = new GZIPOutputStream(output);
		}

		@Override
		public void write(int b) throws IOException {
			gzipOutputStream.write(b);
		}

		@Override
		public void flush() throws IOException {
			gzipOutputStream.flush();
		}

		@Override
		public void close() throws IOException {
			gzipOutputStream.close();
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
		}
	}
}

