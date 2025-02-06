interface ButtonProps{
    label: string;
    onClick?: () => void;
}

const ActionButton: React.FC<ButtonProps> = ({label, onClick}) => {
    return (
        <button
            type="button"
            className="w-56 mx-auto rounded bg-sky-950 text-white text-sm font-medium h-8 hover:bg-sky-900"
            onClick={onClick}
        >
            {label}
        </button>
    );
}

export  default ActionButton;
