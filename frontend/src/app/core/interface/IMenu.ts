export interface IMenu {
    text: string,
    icon: string,
    url?: string;
    children: IMenuItem[]
}
export interface IMenuItem {
    text: string,
    icon: string,
    url: string;
}