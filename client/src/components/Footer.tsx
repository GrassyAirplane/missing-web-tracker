import './Footer.css'

const Footer = () => {
    return (
        <div className="footer">
            By Team Cabbage, 2023.
            <div className="made-with">
                <span className="made-with">Made with</span>
                <ul className="made-with">
                    <li>Google Maps API</li>
                    <li>React</li>
                    <li>Sweet Alert</li>
                    <li>Spring Boot</li>
                </ul>
            </div>
        </div>
    )
}

export default Footer