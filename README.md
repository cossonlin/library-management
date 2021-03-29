# Project Title

Library management system

## Getting Started

### Prerequisites

* [JDK 8](http://www.oracle.com/technetwork/pt/java/javase/overview/index.html) - Ensure JAVA_HOME environment variable
  is set and points to your JDK installation

* [Maven](https://maven.apache.org/) - Dependency Management Download from https://maven.apache.org/

### Installing & Running the tests

Run maven clean install command will install the dependencies, compile and run the tests

```
mvn clean install
```

### And coding style tests

Test cases cover most of scenario

```
@Test
void fetchBySpec_ShouldReturnBookList_WhenSpecIsProvided() {
	Book book1 = new Book();
	book1.setIbsn("i");
	book1.setName("name1");
	Book book2 = new Book();
	book2.setIbsn("i");
	book2.setName("name2");
	List<Book> bookList = new ArrayList<>();
	bookList.add(book1);
	bookList.add(book1);
	Specification querySpec = mock(Specification.class);
	when(bookRepository.findAll(any(Specification.class))).thenReturn(bookList);

	List<Book> bookList1 = bookService.searchBooksBySpec(querySpec);

	assertEquals(2L, bookList1.size());
	verify(bookRepository, times(1)).findAll(querySpec);
}
```

## Deployment

JAR package will be created under target/library-management-{versionNo}.jar after packaging then you can run below
command to bring up the application

```
java -jar target/library-management-{versionNo}.jar
```

## Usage

Postman collection library-management.postman_collection can be found at resources folder

### Create user

```
POST http://localhost:8080/users
```

User info in the RequestBody will be saved into DB

### Add book

```
POST http://localhost:8080/books
```

### Search book

```
GET http://localhost:8080/books?ibsn=ibsnA&name=nameA
```

Search data by ibsn or name

### Update book

```
PUT http://localhost:8080/books
```

### Delete book

```
DELETE http://localhost:8080/books
```

### Borrow book

```
POST http://localhost:8080/books/borrow
```

### Return book

```
POST http://localhost:8080/books/return
```

## Built With

* [Maven](https://maven.apache.org/)

## Authors

* **Lin Lin**