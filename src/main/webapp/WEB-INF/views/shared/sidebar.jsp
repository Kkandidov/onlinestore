<h1 class="my-4">Categories</h1>
    <div class="list-group">
        <c:forEach items="${categories}" var="category">
            <a href="${contextRoot}/?command=show.products.category&id=${category.id}" class="list-group-item"
            id="a_${category.name}">${category.name}</a>
        </c:forEach>
    </div>