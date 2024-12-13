import { useState } from "react";

function DetailsWrapper(props) {
    const [likes, setLikes] = useState(0);

    const increaseLikes = () => {
       setLikes(likes + 1);
    };

    return <>{props.render(likes, increaseLikes)}</>
} 

export default DetailsWrapper;