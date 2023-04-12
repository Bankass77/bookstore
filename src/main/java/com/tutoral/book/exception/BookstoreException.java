package com.tutoral.book.exception;

public class BookstoreException {

	public static class ServiceDownException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ServiceDownException(String message) {
			super(message);
		}
	}

	public static class ServiceDeniedException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ServiceDeniedException(String message) {
			super(message);
		}
	}
}
