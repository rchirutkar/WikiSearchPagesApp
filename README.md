# WikiSearchPagesApp
The Basic goal is to write a native Android application that implements an "Image Search" experience for English Wikipedia. The user will type a search term into an EditText field, and as they type they see pages whose titles start with the entered text (see API details below) represented solely as images. Most importantly, use animations to deliver a smooth, pleasant, and performance experience.

API Details:
This query will fetch 50 pages with associated images in JSON format:
https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=50&pilimit=50&generator=prefixsearch&gpssearch=<your search term>

This will fetch 50 pages with associated images in JSON format.
gpssearch - parameter specifies the search term
 pithumbsize - specifies the max width (or height, whichever is larger) of images returned
More details, and an interactive query builder, can be found here:
https://en.wikipedia.org/wiki/Special:ApiSandbox
Some pages might not have an associated image - use an appropriate placeholder instead.
