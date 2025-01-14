interface ButtonProps{
    label: string;
}

const SubmitButton: React.FC<ButtonProps> = ({label}) => {
    return (
        <button
            type="submit"
            className="w-full m-auto rounded bg-sky-950 text-white text-sm font-medium h-8 hover:bg-sky-900"
        >
            {label}
        </button>
    );
}

export  default SubmitButton;
