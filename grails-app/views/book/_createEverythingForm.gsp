<%@ page import="cscie56.demo.Book" %>
<%@ page import="cscie56.demo.Author" %>
<%@ page import="cscie56.demo.Publisher" %>


<h2>Book</h2>


<div class="fieldcontain ${hasErrors(bean: cmd.bookInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="book.title.label" default="Title" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cmd.bookInstance.title" required="" value="${cmd.bookInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.bookInstance, field: 'isbn', 'error')} required">
    <label for="isbn">
        <g:message code="book.isbn.label" default="Isbn" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cmd.bookInstance.isbn" required="" value="${cmd.bookInstance?.isbn}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.bookInstance, field: 'dateOfPublication', 'error')} required">
    <label for="dateOfPublication">
        <g:message code="book.dateOfPublication.label" default="Date Published" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="text" placeholder="MM/dd/yyyy" name="cmd.bookInstance.dateOfPublication" value="${cmd.bookInstance?.dateOfPublication}"  />

</div>

<h2>Author</h2>

<div class="fieldcontain ${hasErrors(bean: cmd.authorInstance, field: 'firstName', 'error')} required">
    <label for="firstName">
        <g:message code="author.firstName.label" default="First Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cmd.authorInstance.firstName" required="" value="${cmd.authorInstance?.firstName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.authorInstance, field: 'lastName', 'error')} required">
    <label for="lastName">
        <g:message code="author.lastName.label" default="Last Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cmd.authorInstance.lastName" required="" value="${cmd.authorInstance?.lastName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.authorInstance, field: 'birthDate', 'error')} required">
    <label for="birthDate">
        <g:message code="author.birthDate.label" default="Birth Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field type="text" placeholder="MM/dd/yyyy" name="cmd.authorInstance.birthDate" value="${cmd.authorInstance?.birthDate}"/>

</div>


<h2>Publisher</h2>

<div class="fieldcontain ${hasErrors(bean: cmd.publisherInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="publisher.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cmd.publisherInstance.name" required="" value="${cmd.publisherInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.publisherInstance, field: 'type', 'error')} required">
    <label for="cmd.publisherInstance.type">
        <g:message code="publisher.type.label" default="Type" />
        <span class="required-indicator">*</span>
    </label>
    <input type="text" id="cmd.publisherInstance.type" name="cmd.publisherInstance.type" value="${cmd.publisherInstance.type}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cmd.publisherInstance, field: 'dateEstablished', 'error')} required">
    <label for="dateEstablished">
        <g:message code="publisher.dateEstablished.label" default="Date Established" />
        <span class="required-indicator">*</span>
    </label>
    <g:field type="text" placeholder="MM/dd/yyyy" name="cmd.publisherInstance.dateEstablished" precision="day"  value="${cmd.publisherInstance?.dateEstablished}"  />

</div>



