import React, { useEffect, useState } from 'react';
import './css/AnimatedTextSlider.css'

const texts = ['Pro freelancery', 'Pro kreativce', 'Pro tebe'];

export default function AnimatedTextSlider() {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setIndex((prevIndex) => (prevIndex + 1) % texts.length);
    }, 4000);

    return () => clearTimeout(timeout);
  }, [index]);

  return (
    <div className="text-slider-wrapper">
      <div key={index} className="text-slide animate-in">
        {texts[index]}
      </div>
    </div>
  );
}
