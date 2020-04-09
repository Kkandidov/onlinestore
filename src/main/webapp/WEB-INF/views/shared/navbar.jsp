<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${contextRoot}?command=home">Online Store</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
        aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${contextRoot}/?command=home">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item" id="about">
                    <a class="nav-link" href="${contextRoot}/?command=about">About</a>
                </li>
                <li class="nav-item" id="contact">
                    <a class="nav-link" href="${contextRoot}/?command=contact">Contact</a>
                </li>
                <li class="nav-item" id="listProducts">
                    <a class="nav-link" href="${contextRoot}/?command=show.all.products">View Products</a>
                </li>
            </ul>
        </div>
    </div>
</nav>