<div class="container">
    <!-- Breadcrumb -->
	<div class="row">
	    <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
			    <li class="breadcrumb-item"><a href="${contextRoot}/?command=home">Home</a></li>
				<li class="breadcrumb-item"><a href="${contextRoot}/?command=show.all.products">Products</a></li>
				<li class="breadcrumb-item active" aria-current="page">${product.name}</li>
            </ol> 
		</nav> 		
	</div>
	<div class="row">
	    <!-- Display the product image --> 
		<div class="col-lg-9">
			<div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
					<c:forEach items="${views}" var="view">
						<li data-target="#carouselExampleIndicators" data-slide-to="${view.id}"></li>
					</c:forEach>
				</ol>
				<div class="carousel-inner" role="listbox">
					<div class="carousel-item active">
						<img class="d-block img-fluid" src="${contextRoot}/assets/images/products/${product.id}/${product.code}.jpeg">
					</div>
					<c:forEach items="${views}" var="view">
						<div class="carousel-item">
							<img class="d-block img-fluid" src="${contextRoot}/assets/images/products/${product.id}/${view.code}.jpeg">
						</div>
					</c:forEach>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>

		<!-- Display the product description -->
		<div class="col-lg-3">
		    <h3>${product.name}</h3>
			<hr/>
			<c:if test="${description.screen != ''}">
				<p><strong>Screen:</strong> ${description.screen}</p>
			</c:if>
			<c:if test="${description.color != ''}">
				<p><strong>Color:</strong> ${description.color}</p>
			</c:if>
			<c:if test="${description.processor != ''}">
				<p><strong>Processor:</strong> ${description.processor}</p>
			</c:if>
			<c:if test="${description.frontCamera != ''}">
				<p><strong>Front Camera:</strong> ${description.frontCamera}</p>
			</c:if>
			<c:if test="${description.rearCamera != ''}">
				<p><strong>Rear Camera:</strong> ${description.rearCamera}</p>
			</c:if>
			<c:if test="${description.capacity != ''}">
				<p><strong>Capacity:</strong> ${description.capacity}</p>
			</c:if>
			<c:if test="${description.battery != ''}">
				<p><strong>Battery:</strong> ${description.battery}</p>
			</c:if>
			<c:if test="${description.displayTechnology != ''}">
				<p><strong>Display Technology:</strong> ${description.displayTechnology}</p>
			</c:if>
			<c:if test="${description.graphics != ''}">
				<p><strong>Graphics:</strong> ${description.graphics}</p>
			</c:if>
			<c:if test="${description.wirelessCommunication != ''}">
				<p><strong>Wireless Comunication:</strong> ${description.wirelessCommunication}</p>
			</c:if>

			<h4>Price: $ <strong>${product.unitPrice}</strong></h4>
			<hr/>

			<c:choose>
			    <c:when test="${product.quantity < 1}">
				    <h6>Qty. Available: <span style = "color:red">Out of Stock</span></h6>
				</c:when>
                <c:otherwise>
				    <h6>Qty. Available: ${product.quantity}</h6>
				</c:otherwise>
			</c:choose>
			
			<a href="${contextRoot}/?command=show.all.products" class="btn btn-primary">
			    Continue Shopping</a>
		</div>			
	</div>
	
</div>