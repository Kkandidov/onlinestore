<div class="container">
    <div class="row">
		<div class="col-lg-3">
			<%@include file="shared/sidebar.jsp"%>
		</div>
		<div class = "col-lg-9">
            <!-- Breadcrumb component -->
			<div class="row">
			    <nav aria-label="breadcrumb">
				    <c:if test="${allProductsClicked == true}">
				        <ol class="breadcrumb">
					        <li class="breadcrumb-item"><a href="${contextRoot}/home">Home</a></li>
						    <li class="breadcrumb-item active" aria-current="page">All Products</li>
					    </ol>
                    </c:if>
				</nav>
			</div>
			
			<div class="row">
			   <div class="col-lg-12">
					<div class="container-fluid">
						<div class="table-responsive">
							<table id="productListTable" class="table table-striped table-borderd">
								<thead>
									<tr>
										<th></th>
										<th>Name</th>
										<th>Brand</th>
										<th>Price</th>
										<th>Qty. Available</th>
										<th></th>
									</tr>  								
								</thead>  
						
								<tfoot>
									<tr>
										<th></th>
										<th>Name</th>
										<th>Brand</th>
										<th>Price</th>
										<th>Qty. Available</th>
										<th></th>
									</tr>  								
								</tfoot>  
							</table> 							
						</div>
					</div>
			   </div>
			</div>
		</div>
	</div>
</div>