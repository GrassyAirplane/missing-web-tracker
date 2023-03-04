import './SearchBar.css'

const SearchBar = () => {
    return (
        <div className="search-bar">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
            <ul>
                <li className="search-bar-btn">Near me</li>
                <li className="search-bar-btn">Add missing</li>
                <li>
                    <form>
                        <input type="text" placeholder="Find missing"></input>
                        <button type="submit"><i className="fa fa-search"></i></button>
                    </form>
                </li>
            </ul>
        </div>
    )
}

export default SearchBar