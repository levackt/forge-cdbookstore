package org.cdbookstore.rest;

import org.apache.commons.lang.RandomStringUtils;
import org.cdbookstore.model.Author;
import org.cdbookstore.model.Book;
import org.cdbookstore.model.Language;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;
import static javax.ws.rs.core.Response.Status.*;

@RunWith(Arquillian.class)
public class BookEndpointIntegrationTest {

	@Inject
	private BookEndpoint bookEndpoint;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(BookEndpoint.class)
				.addClass(RandomStringUtils.class)
				.addPackage(Book.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void should_be_deployed() {
		Assert.assertNotNull(bookEndpoint);
	}
	
	@Test
	public void testCrud(){

        // Create 
		Book book = new Book();
		Author author = new Author();
		author.setFirstName("First");
		book.setDescription("Desc");
		book.setLanguage(Language.ENGLISH);
		book.setNbOfPages(444);
		book.setPrice(50.05f);
		book.setPublicationDate(new Date());
		book.setTitle(RandomStringUtils.randomAlphabetic(5));
		book.setAuthor(author);
		Response response = bookEndpoint.create(book);
        assertEquals(CREATED.getStatusCode(), response.getStatus());

        // Read
		response = bookEndpoint.findById(book.getId());
        assertEquals(OK.getStatusCode(), response.getStatus());
        Book dbBook = (Book) response.getEntity();
        assertNotNull(dbBook);
        assertEquals(book.getAuthor().getFirstName(), dbBook.getAuthor().getFirstName());

        System.out.println("dbBook = " + dbBook);
        
        // Update
        dbBook.setPrice(500.5f);
        bookEndpoint.update(dbBook.getId(), dbBook);
        response = bookEndpoint.findById(book.getId());
        dbBook = (Book) response.getEntity();
        assertEquals(new Float(500.5f), dbBook.getPrice());

        // Delete 
		response = bookEndpoint.deleteById(book.getId());       
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
        
        response = bookEndpoint.findById(book.getId());
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
        dbBook = (Book) response.getEntity();
        assertNull(dbBook);
	}
}
