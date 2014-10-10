<@html>

<style>
  .author { padding-left: 10px; font-style: italic; }
</style>

<ul id="books">
  <#list books as book>
    <li class="book">
      <span class="title">${book.title}</span>
      <span class="author">${book.author}</span>
    </li>
  </#list>
</ul>

</@html>
